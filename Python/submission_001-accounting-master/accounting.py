import user.authentication
import transactions.journal
import banking
import sys

if len(sys.argv) > 1:
    sys.argv.pop(0)
    print("\n".join(sys.argv))

user.authentication.authenticate_user()
transactions.journal.receive_income(100)
transactions.journal.pay_expense(100)
# banking.reconciliation.do_reconciliation()
banking.fvb.reconciliation.do_reconciliation()
# banking.ubsa.reconciliation.do_reconciliation()
# banking.online.reconciliation.do_reconciliation()

# help("modules")