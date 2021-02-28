package contracts.user.get

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Find user with to short input"
    description """
    Represents the search for an existing user by his name:

    '''
    given:
        short username
    when:
        search user by username
    then:
        return error
    '''
    """

    request {
        url "/api/user/1"
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
