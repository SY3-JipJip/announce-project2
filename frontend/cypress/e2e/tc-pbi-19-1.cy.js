describe('TC-PBI19-DELETE-USER-NO-AUTHORIZATION-CONTROL-1\n Senario : normal - confirm', () => {
      
    it('should visit /admin/user',()=>{
        cy.visit('/admin/user')

        cy.wait(200)
    })
    
    
    it('should have username "kanokwan".',()=>{
        cy.visit('/admin/user')

        cy.wait(200)
        cy.get('.ann-username').contains('kanokwan').parent('.ann-item')
    })

    it('should have delete button and click the delete buttonใ',()=>{
        cy.visit('/admin/user')

        cy.wait(200)
        cy.get('.ann-username').contains('kanokwan').parent('.ann-item').as('ann-1')
        cy.get('@ann-1').contains('.ann-button','delete').should('exist').click()

        cy.on("window:confirm", (message) => {
            expect(message).to.contain("delete");
            return true;
        });
    })
})