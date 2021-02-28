package contracts.user.post

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Create new user with invalid mail"
    description """
    Represents the following error case meanwhile a user registration:

    '''
    given:
        user has invalid mail
    when:
        registration of user
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
                username: "NewUser",
                email: "notAnMail",
                firstname: "New",
                lastname: "User"
        )
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body (
                email: "must be a well-formed email address"
        )
    }
}
