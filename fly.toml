app = "web-electromartshop"
primary_region = "sin"

[build]
dockerfile = "Dockerfile"

[env]
VNP_RETURN_URL = "https://web-electromartshop.fly.dev/payment-return"
VNP_TMN_CODE = "K1SH6864"
VNP_HASH_SECRET = "1J64G1DKLNTS3B30FSYO6XSPMW6QBE7E"

[http_service]
internal_port = 8080
force_https = true

[[vm]]
cpu_kind = "shared"
cpus = 1
memory_mb = 512
