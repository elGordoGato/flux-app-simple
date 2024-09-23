FROM ghcr.io/william-yeh/wrk:4.2.0

# Set the working directory
WORKDIR /data

# Copy the iterative.lua script to the container
COPY iterative.lua .

# Command to run wrk with the iterative.lua script
CMD ["-t2","-c5", "-d5s", "--timeout", "2s", "-s", "iterative.lua", "http://host.docker.internal:8080"]