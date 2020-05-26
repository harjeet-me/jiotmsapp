import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { InvoiceHistoryComponent } from './invoice-history.component';
import { InvoiceHistoryDetailComponent } from './invoice-history-detail.component';
import { InvoiceHistoryUpdateComponent } from './invoice-history-update.component';
import { InvoiceHistoryDeleteDialogComponent } from './invoice-history-delete-dialog.component';
import { invoiceHistoryRoute } from './invoice-history.route';

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(invoiceHistoryRoute)],
  declarations: [
    InvoiceHistoryComponent,
    InvoiceHistoryDetailComponent,
    InvoiceHistoryUpdateComponent,
    InvoiceHistoryDeleteDialogComponent,
  ],
  entryComponents: [InvoiceHistoryDeleteDialogComponent],
})
export class JiotmsappInvoiceHistoryModule {}
