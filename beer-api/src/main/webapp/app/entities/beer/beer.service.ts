import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBeer } from 'app/shared/model/beer.model';

type EntityResponseType = HttpResponse<IBeer>;
type EntityArrayResponseType = HttpResponse<IBeer[]>;

@Injectable({ providedIn: 'root' })
export class BeerService {
  public resourceUrl = SERVER_API_URL + 'api/beers';

  constructor(protected http: HttpClient) {}

  create(beer: IBeer): Observable<EntityResponseType> {
    return this.http.post<IBeer>(this.resourceUrl, beer, { observe: 'response' });
  }

  update(beer: IBeer): Observable<EntityResponseType> {
    return this.http.put<IBeer>(this.resourceUrl, beer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
