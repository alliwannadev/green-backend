rootProject.name = "green-backend"
include(
    "core:core-domain",
    "core:core-api",
    "core:core-consumer",
    "core:core-consumer:coupon-consumer",
    "supports:data-serializer",
    "supports:snowflake",
    "supports:event",
    "supports:transactional-outbox",
    "supports:distributed-lock"
)
