package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(2)
    description """
    Represents the following error case meanwhile a user registration with to long:

    '''
    given:
        user with to long firstname, lastname and username
    when:
        registration of a user
    then:
        user registration refused
    '''
    """

    request {
        url "/api/user"
        method POST()
        headers {
            contentType applicationJson()
        }
        body (
                username: regex('\\w{33}'),
                email: "notInTest@example.org",
                firstname: regex('\\w{33}'),
                lastname: regex('\\w{33}')
        )
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body(
                firstname: "size must be between 2 and 32",
                username: "size must be between 2 and 32",
                lastname: "size must be between 2 and 32"
        )
    }
}
