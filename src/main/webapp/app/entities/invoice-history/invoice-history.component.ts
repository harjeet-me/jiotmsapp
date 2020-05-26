import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceHistory } from 'app/shared/model/invoice-history.model';
import { InvoiceHistoryService } from './invoice-history.service';
import { InvoiceHistoryDeleteDialogComponent } from './invoice-history-delete-dialog.component';

@Component({
  selector: 'jhi-invoice-history',
  templateUrl: './invoice-history.component.html',
})
export class InvoiceHistoryComponent implements OnInit, OnDestroy {
  invoiceHistories?: IInvoiceHistory[];
  eventSubscriber?: Subscription;

  constructor(
    protected invoiceHistoryService: InvoiceHistoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.invoiceHistoryService.query().subscribe((res: HttpResponse<IInvoiceHistory[]>) => (this.invoiceHistories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvoiceHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceHistoryListModification', () => this.loadAll());
  }

  delete(invoiceHistory: IInvoiceHistory): void {
    const modalRef = this.modalService.open(InvoiceHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceHistory = invoiceHistory;
  }
}
