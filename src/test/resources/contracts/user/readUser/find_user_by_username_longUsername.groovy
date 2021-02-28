package contracts.user.get

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Find user with to long input"
    description """
    Represents the search for an existing user by his name:

    '''
    given:
        long username
    when:
        search user by username
    then:
        return error
    '''
    """

    request {
        url "/api/user/123456789012345678901234567890123"
        method GET()
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body(
                username: "size must be between 2 and 32"
        )
    }
}
