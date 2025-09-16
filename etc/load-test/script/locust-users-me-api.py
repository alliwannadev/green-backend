import random
import string
from locust import task, FastHttpUser

class GetUsersMe(FastHttpUser):
    @task
    def callUsersMeApi(self):
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

        with self.rest("GET", "/v1/users/me", headers):
            pass
