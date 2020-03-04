# How deploy postgresql to Cloud Run

cd postgres
gcloud auth login
gcloud config set project tavern-meetup
gcloud builds submit -t gcr.io/tavern-meetup/postgres-tavern-meetup:latest
