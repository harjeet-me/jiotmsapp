import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarrier } from 'app/shared/model/carrier.model';
import { CarrierService } from './carrier.service';
import { CarrierDeleteDialogComponent } from './carrier-delete-dialog.component';

@Component({
  selector: 'jhi-carrier',
  templateUrl: './carrier.component.html',
})
export class CarrierComponent implements OnInit, OnDestroy {
  carriers?: ICarrier[];
  eventSubscriber?: Subscription;

  constructor(
    protected carrierService: CarrierService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.carrierService.query().subscribe((res: HttpResponse<ICarrier[]>) => (this.carriers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCarriers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICarrier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCarriers(): void {
    this.eventSubscriber = this.eventManager.subscribe('carrierListModification', () => this.loadAll());
  }

  delete(carrier: ICarrier): void {
    const modalRef = this.modalService.open(CarrierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.carrier = carrier;
  }
}
