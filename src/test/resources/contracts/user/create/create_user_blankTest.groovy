package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(2)
    description """
    Represents the following error case meanwhile a user registration with blank values:

    '''
    given:
        user with blank values
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
                username: "",
                email: "",
                firstname: "",
                lastname: ""
        )
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body(
                firstname: "size must be between 2 and 32",
                email: "must not be blank",
                // TODO: Improve validation handling message
                username: anyOf("must not be blank", "size must be between 2 and 32"),
                lastname: "size must be between 2 and 32"
        )
    }
}
