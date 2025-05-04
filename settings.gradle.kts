rootProject.name = "green-backend"
include(
    "core",
    "core:core-api",
    "supports",
    "supports:data-serializer",
    "supports:snowflake",
    "supports:event",
    "supports:transactional-outbox"
)
