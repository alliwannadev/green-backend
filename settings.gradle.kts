rootProject.name = "green-backend"
include(
    "core:core-domain",
    "core:core-api",
    "core:core-consumer",
    "core:core-consumer:coupon-consumer",
    "support:data-serializer",
    "support:snowflake",
    "support:event",
    "support:transactional-outbox",
    "support:distributed-lock"
)
