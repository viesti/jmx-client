#!/bin/sh

java -cp {{dest_dir}}/clojure-1.7.0-beta3.jar:{{dest_dir}}/java.jmx-0.3.1.jar:{{dest_dir}}/tools.cli-0.3.1.jar clojure.main {{dest_dir}}/jmx-client.clj "$@"
