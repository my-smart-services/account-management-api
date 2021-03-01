package contracts.user.readUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description """
    Represents the search for an user by a name shorter than 2:

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
