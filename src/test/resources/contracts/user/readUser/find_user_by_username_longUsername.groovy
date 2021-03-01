package contracts.user.readUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description """
    Represents the search for an user with a name longer than 32:

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
                "readUser.username": "size must be between 2 and 32"
        )
    }
}
