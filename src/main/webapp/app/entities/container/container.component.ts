import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContainer } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';
import { ContainerDeleteDialogComponent } from './container-delete-dialog.component';

@Component({
  selector: 'jhi-container',
  templateUrl: './container.component.html',
})
export class ContainerComponent implements OnInit, OnDestroy {
  containers?: IContainer[];
  eventSubscriber?: Subscription;

  constructor(protected containerService: ContainerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.containerService.query().subscribe((res: HttpResponse<IContainer[]>) => (this.containers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContainers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContainer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContainers(): void {
    this.eventSubscriber = this.eventManager.subscribe('containerListModification', () => this.loadAll());
  }

  delete(container: IContainer): void {
    const modalRef = this.modalService.open(ContainerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.container = container;
  }
}
