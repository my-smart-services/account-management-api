package contracts.user.post

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Create user with null values"
    description """
    Represents the following error case meanwhile a user registration:

    '''
    given:
        all values are null
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
                username: null,
                email: null,
                firstname: null,
                lastname: null
        )
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body(
                email: "must not be blank",
                username: "must not be blank"
        )
    }
}
