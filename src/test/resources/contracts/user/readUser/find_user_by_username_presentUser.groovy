package contracts.user.get

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Find existing user by username"
    description """
    Represents the search for an existing user by his name:

    '''
    given:
        user for username is registered
    when:
        search user by username
    then:
        return user for the username
    '''
    """

    request {
        url "/api/user/MaxMust"
        method GET()
    }

    response {
        status OK()
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
}
