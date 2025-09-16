import random
import string
from locust import task, FastHttpUser

class ProductWarehousing(FastHttpUser):
    @task
    def callProductWarehousingApi(self):
        randomQuantity = random.randint(1, 100)
        jwtTokens = [
            "jwtTokenValue1",
            "jwtTokenValue2",
            "jwtTokenValue3",
            "jwtTokenValue4",
            "jwtTokenValue5",
        ]
        selectedJwtToken = random.choice(jwtTokens)
        headers = {
            "Authorization": f"Bearer {selectedJwtToken}",
            "Content-Type": "application/json"
        }
        payload = {
            "productOptionCombinationId": 123,
            "warehousingDate": "20250916",
            "quantity": randomQuantity
        }

        with self.rest("POST", "/v1/warehousing", headers, json=payload):
            pass
