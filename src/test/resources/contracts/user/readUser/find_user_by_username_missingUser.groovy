package contracts.user.readUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(2)
    description """
    Represents the search for an non existing user by his name:

    '''
    given:
        user for username not registered
    when:
        search user by username
    then:
        return error
    '''
    """

    request {
        url "/api/user/NotAnExistingUser"
        method GET()
    }

    response {
        status NOT_FOUND()
    }
}
