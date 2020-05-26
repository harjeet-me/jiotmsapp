import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { InvoiceItemDeleteDialogComponent } from './invoice-item-delete-dialog.component';

@Component({
  selector: 'jhi-invoice-item',
  templateUrl: './invoice-item.component.html',
})
export class InvoiceItemComponent implements OnInit, OnDestroy {
  invoiceItems?: IInvoiceItem[];
  eventSubscriber?: Subscription;

  constructor(
    protected invoiceItemService: InvoiceItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.invoiceItemService.query().subscribe((res: HttpResponse<IInvoiceItem[]>) => (this.invoiceItems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvoiceItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceItemListModification', () => this.loadAll());
  }

  delete(invoiceItem: IInvoiceItem): void {
    const modalRef = this.modalService.open(InvoiceItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceItem = invoiceItem;
  }
}
