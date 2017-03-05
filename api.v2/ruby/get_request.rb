GET https://api.practitest.com/api/v2/projects.json?api_token=xx&developer_email=admin%40pt.com&page[number]=1&page[size]=2

require 'net/http'
require 'net/https'
require 'uri'
require 'json'

URL   = "https://api.practitest.com"
TOKEN = "xxx"
DEVELOPER_EMAIL= "my@mail.address"

uri = URI.parse("#{URL}/api/v2/projects.json")
http = Net::HTTP.new(uri.host, uri.port)
http.use_ssl = true
req = Net::HTTP::Get.new(uri.path)
req.basic_auth DEVELOPER_EMAIL, TOKEN
res = http.request(req)
puts res.body
