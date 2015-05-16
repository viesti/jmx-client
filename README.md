# jmx-client
Stand alone (no Leiningen) Clojure script to print out JMX beans attributes

Install

    ansible-playbook -i 'localhost,' -e dest_dir=/tmp/foobar install.yml

Run

    0% /tmp/foobar/jmx-client.sh -p 9997 -f "kafka*:*"
    NetworkProcessorAvgIdlePercent.MeanRate 0.9999327621059828 1431807861
    NetworkProcessorAvgIdlePercent.OneMinuteRate 1.0000679018952472 1431807861
    NetworkProcessorAvgIdlePercent.FiveMinuteRate 0.9999536665090386 1431807861
    NetworkProcessorAvgIdlePercent.FifteenMinuteRate 0.9985703211120902 1431807861
    NetworkProcessorAvgIdlePercent.Count 2865920985551 1431807861
    BytesOutPerSec.MeanRate 0.0 1431807861
    ...
