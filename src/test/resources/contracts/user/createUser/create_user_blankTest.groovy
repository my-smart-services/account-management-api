package contracts.user.post

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Create user with blank values"
    description """
    Represents the following error case meanwhile a user registration:

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
                username: anyNonBlankString(),
                lastname: "size must be between 2 and 32"
        )
    }
}
