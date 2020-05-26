import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyProfile } from 'app/shared/model/company-profile.model';
import { CompanyProfileService } from './company-profile.service';
import { CompanyProfileDeleteDialogComponent } from './company-profile-delete-dialog.component';

@Component({
  selector: 'jhi-company-profile',
  templateUrl: './company-profile.component.html',
})
export class CompanyProfileComponent implements OnInit, OnDestroy {
  companyProfiles?: ICompanyProfile[];
  eventSubscriber?: Subscription;

  constructor(
    protected companyProfileService: CompanyProfileService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.companyProfileService.query().subscribe((res: HttpResponse<ICompanyProfile[]>) => (this.companyProfiles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompanyProfiles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompanyProfile): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCompanyProfiles(): void {
    this.eventSubscriber = this.eventManager.subscribe('companyProfileListModification', () => this.loadAll());
  }

  delete(companyProfile: ICompanyProfile): void {
    const modalRef = this.modalService.open(CompanyProfileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.companyProfile = companyProfile;
  }
}
