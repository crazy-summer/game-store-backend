-- KEYS[1] 是 stockKey，例如 "stock:g001"
local stock = redis.call('GET', KEYS[1])

if not stock or tonumber(stock) <= 0 then
    return -1  -- 明确返回 -1 表示失败
end

redis.call('DECR', KEYS[1])
return 1  -- 返回 1 表示成功