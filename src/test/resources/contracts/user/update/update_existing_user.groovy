package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(3)
    description """
    Represents the update of an existing user:

    '''
    given:
        input is valid and user registered
    when:
        an user is updated
    then:
        user is updated
    '''
    """

    request {
        url "/api/user/MaxMust"
        method PUT()
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
        status ACCEPTED()
        headers {
            contentType applicationJson()
        }
        body(request.body)
    }
}
