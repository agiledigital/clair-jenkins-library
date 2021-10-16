#
# Play 2.8 Build Image
# Docker image with libraries and tools as required for building Play 2.8 rojects using SBT 
#

FROM golang:alpine3.14

LABEL Description="Docker image with clairctl used in scanning images in jenkins" Vendor="Agile Digital" Version="0.1"

RUN GO111MODULE=on go get github.com/quay/clair/v4/cmd/clairctl@latest

ENV HOME /home/jenkins

RUN addgroup --system --gid 1000 jenkins
RUN adduser --system --uid 1000 --home $HOME --ingroup jenkins jenkins


WORKDIR /home/jenkins

USER jenkins