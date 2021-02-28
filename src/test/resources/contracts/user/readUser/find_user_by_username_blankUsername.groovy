package contracts.user.get

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Find user with short input"
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
        url "/api/user/"
        method GET()
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body(
                username: "must not be blank"
        )
    }
}
