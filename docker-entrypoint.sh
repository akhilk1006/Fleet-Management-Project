#!/bin/sh

mkdir trucker-api/config
touch trucker-api/config/application.properties

if [ -d /run/secrets/ ]; then
    for filename in /run/secrets/*; do
        echo "${filename##*/}=`cat $filename`" >> trucker-api/config/application.properties
    done
fi

echo "Properties created, running $@"

sh -c "$@"