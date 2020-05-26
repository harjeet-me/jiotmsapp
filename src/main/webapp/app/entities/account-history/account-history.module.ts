import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { AccountHistoryComponent } from './account-history.component';
import { AccountHistoryDetailComponent } from './account-history-detail.component';
import { AccountHistoryUpdateComponent } from './account-history-update.component';
import { AccountHistoryDeleteDialogComponent } from './account-history-delete-dialog.component';
import { accountHistoryRoute } from './account-history.route';

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(accountHistoryRoute)],
  declarations: [
    AccountHistoryComponent,
    AccountHistoryDetailComponent,
    AccountHistoryUpdateComponent,
    AccountHistoryDeleteDialogComponent,
  ],
  entryComponents: [AccountHistoryDeleteDialogComponent],
})
export class JiotmsappAccountHistoryModule {}
