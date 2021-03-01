package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(2)
    description """
    Represents the creation of an new user with optional fields.

    '''
    given:
        user is valid and not registered yet
    when:
        registration of a user
    then:
        user is registered
    '''
    """

    request {

        url "/api/user"
        method POST()
        headers {
            contentType applicationJson()
        }
        body (
                username: regex('\\w{2,32}'),
                email: anyEmail(),
                firstname: regex('\\w{2,32}'),
                lastname: regex('\\w{2,32}')
        )
    }

    response {
        status CREATED()
        headers {
            contentType applicationJson()
        }
        body (request.body)
    }
}
