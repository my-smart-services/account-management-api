package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(3)
    description """
    Represents the following error case meanwhile an update of a user:

    '''
    given:
        the username is to short
    when:
        a user is updated
    then:
        update is refused
    '''
    """

    request {
        url '/api/user/MaxMust'
        method PUT()
        headers {
            contentType applicationJson()
        }
        body (
                username: regex('(\\w{1,1}|\\w{33})'),
                email: $(consumer(notMatching(email())), producer(anyNonBlankString())),
                firstname: regex('(\\w{1,1}|\\w{33})'),
                lastname: regex('(\\w{1,1}|\\w{33})')
        )
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body(
                firstname: "size must be between 2 and 32",
                email: "must be a well-formed email address",
                username: "size must be between 2 and 32",
                lastname: "size must be between 2 and 32"
        )
    }
}
