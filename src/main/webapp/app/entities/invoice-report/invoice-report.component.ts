import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceReport } from 'app/shared/model/invoice-report.model';
import { InvoiceReportService } from './invoice-report.service';
import { InvoiceReportDeleteDialogComponent } from './invoice-report-delete-dialog.component';

@Component({
  selector: 'jhi-invoice-report',
  templateUrl: './invoice-report.component.html',
})
export class InvoiceReportComponent implements OnInit, OnDestroy {
  invoiceReports?: IInvoiceReport[];
  eventSubscriber?: Subscription;

  constructor(
    protected invoiceReportService: InvoiceReportService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.invoiceReportService.query().subscribe((res: HttpResponse<IInvoiceReport[]>) => (this.invoiceReports = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInInvoiceReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceReportListModification', () => this.loadAll());
  }

  delete(invoiceReport: IInvoiceReport): void {
    const modalRef = this.modalService.open(InvoiceReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceReport = invoiceReport;
  }
}
