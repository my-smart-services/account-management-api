package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(3)
    description """
    Represents the following error case meanwhile an update of a user:

    '''
    given:
        the username is to long
    when:
        a user is updated
    then:
        update is refused
    '''
    """

    request {
        url regex('/api/user/\\w{33}')
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
        status BAD_REQUEST()
    }
}
