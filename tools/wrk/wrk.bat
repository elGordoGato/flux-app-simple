docker run --rm -v C:\Users\kumar\IdeaProjects\irlix\ipr\flux-app\tools\wrk\iterative.lua:/data/iterative.lua ghcr.io/william-yeh/wrk:4.2.0 -t2 -c5 -d5s --timeout 2s -s /data/iterative.lua http://host.docker.internal:8080
