import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JiotmsappSharedModule } from 'app/shared/shared.module';
import { TripComponent } from './trip.component';
import { TripDetailComponent } from './trip-detail.component';
import { TripUpdateComponent } from './trip-update.component';
import { TripDeleteDialogComponent } from './trip-delete-dialog.component';
import { tripRoute } from './trip.route';

@NgModule({
  imports: [JiotmsappSharedModule, RouterModule.forChild(tripRoute)],
  declarations: [TripComponent, TripDetailComponent, TripUpdateComponent, TripDeleteDialogComponent],
  entryComponents: [TripDeleteDialogComponent],
})
export class JiotmsappTripModule {}
