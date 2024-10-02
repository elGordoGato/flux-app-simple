docker run --rm -v %cd%\iterative.lua:/data/iterative.lua ^
ghcr.io/william-yeh/wrk:4.2.0 ^
-t2 -c1000 -d10s --timeout 2s -s /data/iterative.lua ^
http://host.docker.internal:8080
