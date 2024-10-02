counter = 1
isAsync = true
isBlocking = false

request = function()
    wrk.method = "GET"
    local id = counter
    counter = counter + 1
    if counter > 100 then
        counter = 1
    end
    local path = isAsync and
            ("/children/async/" .. id .. "?isBlocking=" .. tostring(isBlocking)) or
            ("/children/sync/" .. id)
    return wrk.format(nil, path)
end