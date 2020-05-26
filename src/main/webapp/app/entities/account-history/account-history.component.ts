import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountHistory } from 'app/shared/model/account-history.model';
import { AccountHistoryService } from './account-history.service';
import { AccountHistoryDeleteDialogComponent } from './account-history-delete-dialog.component';

@Component({
  selector: 'jhi-account-history',
  templateUrl: './account-history.component.html',
})
export class AccountHistoryComponent implements OnInit, OnDestroy {
  accountHistories?: IAccountHistory[];
  eventSubscriber?: Subscription;

  constructor(
    protected accountHistoryService: AccountHistoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.accountHistoryService.query().subscribe((res: HttpResponse<IAccountHistory[]>) => (this.accountHistories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccountHistories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccountHistory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccountHistories(): void {
    this.eventSubscriber = this.eventManager.subscribe('accountHistoryListModification', () => this.loadAll());
  }

  delete(accountHistory: IAccountHistory): void {
    const modalRef = this.modalService.open(AccountHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountHistory = accountHistory;
  }
}
