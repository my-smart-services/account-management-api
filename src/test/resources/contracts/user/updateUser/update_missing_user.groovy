package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(3)
    description """
    Represents the following error case meanwhile an update of a user:

    '''
    given:
        input is valid but user is unknown
    when:
        a user is updated
    then:
        update is refused
    '''
    """

    request {
        url regex('/api/user/\\w{2,32}')
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
        status NOT_FOUND()
    }
}
