package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description """
    Represents the creation of an new user.

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
                username: anyNonBlankString(),//regex('\\b\\w{2,32}\\b'),
                email: anyEmail(),
                firstname: anyNonBlankString(),//optional(regex('\\b\\w{2,32}\\b')),
                lastname: anyNonBlankString()//optional(regex('\\b\\w{2,32}\\b'))
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
