package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(2)
    description """
    Represents the following error case meanwhile a user registration with an empty body:

    '''
    given:
        empty body
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
        body ("""{}""")
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
