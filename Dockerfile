FROM python:3.7-stretch

RUN apt-get -y update \
    && apt-get -y install build-essential libssl-dev git zlib1g-dev
RUN git clone https://github.com/giltene/wrk2.git wrk2 \
    && cd wrk2 \
    && make

RUN git clone https://github.com/wg/wrk.git wrk \
    && cd wrk \
    && make

RUN cp wrk/wrk /usr/local/bin
RUN mv wrk2/wrk /usr/local/bin/wrk2

HEALTHCHECK CMD exit 0