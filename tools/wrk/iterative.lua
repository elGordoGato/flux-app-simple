counter = 1

request = function()
    wrk.method = "GET"
    local isAsync = true
    local id = counter
    counter = counter + 1
    if counter > 100 then
        counter = 1
    end
    local path = "/children/" .. id .. "?isAsync=" .. tostring(isAsync)
    return wrk.format(nil, path)
end