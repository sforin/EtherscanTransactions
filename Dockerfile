FROM ubuntu:latest
LABEL authors="sfori"

ENTRYPOINT ["top", "-b"]