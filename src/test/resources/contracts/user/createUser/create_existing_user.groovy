package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(2)
    description """
    Represents the following error case meanwhile a user registration of an existing user:

    '''
    given:
        input is valid but user is already registered
    when:
        a new user is registered
    then:
        registration is refused
    '''
    """

    request {
        url "/api/user"
        method POST()
        headers {
            contentType applicationJson()
        }
        body (
                username: "MaxMust",
                email: "max.mustermann@example.org",
                firstname: "Max",
                lastname: "Mustermann"
        )
    }

    response {
        status UNPROCESSABLE_ENTITY()
    }
}
