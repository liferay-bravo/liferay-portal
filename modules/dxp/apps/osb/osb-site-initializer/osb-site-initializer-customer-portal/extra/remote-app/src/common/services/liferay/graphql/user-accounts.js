const getUserAccountById = (id) => {
	return `userAccount(userAccountId: ${id}) {
                id
                name
                image
                externalReferenceCode
                accountBriefs {
                  id
                  name
                  externalReferenceCode
                }
              }`;
};

export {getUserAccountById};
