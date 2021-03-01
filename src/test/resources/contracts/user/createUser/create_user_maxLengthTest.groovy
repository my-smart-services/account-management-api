package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
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
                username: "123456789012345678901234567890123",
                email: "notInTest@example.org",
                firstname: "123456789012345678901234567890123",
                lastname: "123456789012345678901234567890123"
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
